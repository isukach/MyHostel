package by.bsuir.suite.page.person;

import by.bsuir.suite.colors.ColorConstants;
import by.bsuir.suite.dto.person.*;
import by.bsuir.suite.page.base.BasePage;
import by.bsuir.suite.page.base.NotificationWindow;
import by.bsuir.suite.page.base.panel.NavigationPanel;
import by.bsuir.suite.page.person.panel.*;
import by.bsuir.suite.service.person.PersonService;
import by.bsuir.suite.session.HostelAuthenticatedWebSession;
import by.bsuir.suite.session.SessionUser;
import by.bsuir.suite.util.Roles;
import by.bsuir.suite.util.UploadUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormValidatingBehavior;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author i.sukach
 */
public class PersonPage extends BasePage {
    private static final Logger LOG = Logger.getLogger(PersonPage.class);

    public static final String USERNAME_KEY = "userName";
    private static final int RANDOM_STRING_LENGTH = 15;
    private static final Set<String> ALLOWED_EXTENSIONS = new HashSet<String>();
    private static final String[] ROLES_FOR_EDITING = new String[]{
            Roles.SUPER_USER, Roles.REGISTRAR, Roles.FLOOR_HEAD
    };

    private static final String[] ROLES_FOR_VIEWING_STATISTICS = new String[]{
            Roles.FLOOR_HEAD, Roles.EDUCATOR, Roles.SUPER_USER, Roles.REGISTRAR,
            Roles.COMMANDANT, Roles.ADMIN, Roles.YOUTH_CENTER, Roles.MANAGERESS
    };

    static {
        ALLOWED_EXTENSIONS.add("jpg");
        ALLOWED_EXTENSIONS.add("jpeg");
        ALLOWED_EXTENSIONS.add("png");
        ALLOWED_EXTENSIONS.add("bmp");
        ALLOWED_EXTENSIONS.add("gif");
    }

    private FeedbackPanel feedback;
    private Panel infoPanel;
    private Panel avatarPanel;
    private String currentUserName;
    private final ModalWindow notImageDialog;
    private boolean isDifferentUserPage;
    private ProfileNavigationPanel navigationPanel;
    private boolean isEdited;

    @SpringBean
    private PersonService personService;

    private PersonDto personDto;

    private String pageOwner;

    public PersonPage(final PageParameters parameters) {
        super(ColorConstants.GREY);

        org.apache.wicket.authroles.authorization.strategies.role.Roles rolesForEditing
                = new org.apache.wicket.authroles.authorization.strategies.role.Roles(ROLES_FOR_EDITING);
        boolean editingAllowed = ((HostelAuthenticatedWebSession) getSession()).getUser().hasAnyRole(rolesForEditing);
        isEdited = parameters.get("isEdited").toBooleanObject();
        currentUserName = ((HostelAuthenticatedWebSession) getSession()).getUser().getUserName();
        pageOwner = parameters.get(USERNAME_KEY).toString();
        if (pageOwner == null) {
            pageOwner = currentUserName;
        }
        isDifferentUserPage = !currentUserName.equals(pageOwner);
        if (isDifferentUserPage) {
            initPerson(pageOwner);
        } else {
            initPerson(currentUserName);
        }

        notImageDialog = createNotificationWindow("modal", "warning", "warning.notAnImage", false);
        add(notImageDialog);

        Form form = new Form<PersonDto>("form", new CompoundPropertyModel<PersonDto>(personDto));
        add(form);

        feedback = new FeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        form.add(feedback);

        if (!isEdited && !isDifferentUserPage) { //person browses its own profile
            form.add(new EmptyPanel("rolePanel"));
            form.add(new EmptyPanel("navigationPanel"));
            form.add(new ToEditButtonPanel("buttonPanel", personDto));
            avatarPanel = new AvatarPanelView("avatarPanel", personDto.getAvatarPath());
            infoPanel = new InfoPanelView("infoPanel", personDto);
            form.add(generateLinksPanel());
        } else if (!editingAllowed && !isEdited && isDifferentUserPage) { // someone without editing rights browsing
            form.add(new EmptyPanel("rolePanel"));
            form.add(new EmptyPanel("navigationPanel"));
            form.add(new EmptyPanel("buttonPanel"));
            avatarPanel = new AvatarPanelView("avatarPanel", personDto.getAvatarPath());
            infoPanel = new InfoPanelView("infoPanel", personDto);
            form.add(generateLinksPanel());
        } else if (editingAllowed && isEdited && isDifferentUserPage) { // someone with editing rights is editing
            form.add(new RoomerRolePanel("rolePanel"));
            navigationPanel = new ProfileNavigationPanel("navigationPanel");
            navigationPanel.setOutputMarkupId(true);
            navigationPanel.initializeSelectedFields();
            form.add(navigationPanel);
            form.add(new EditButtonPanel("buttonPanel"));
            avatarPanel = new AvatarPanelEdit("avatarPanel", personDto.getAvatarPath());
            infoPanel = new InfoPanelEditByAdmin("infoPanel", personDto);
            form.add(generateLinksPanel());
        } else if (editingAllowed && !isEdited && isDifferentUserPage) { // someone with editing rights is browsing
            form.add(new EmptyPanel("rolePanel"));
            form.add(new EmptyPanel("navigationPanel"));
            form.add(new ToAdminButtonPanel("buttonPanel", pageOwner));
            avatarPanel = new AvatarPanelView("avatarPanel", personDto.getAvatarPath());
            infoPanel = new InfoPanelView("infoPanel", personDto);
            form.add(generateLinksPanel());
        } else { // person is editing its own profile
            form.add(new EmptyPanel("rolePanel"));
            form.add(new EmptyPanel("navigationPanel"));
            form.add(new EditButtonPanel("buttonPanel"));
            avatarPanel = new AvatarPanelView("avatarPanel", personDto.getAvatarPath());
            infoPanel = new InfoPanelEditByUser("infoPanel", personDto);
            form.add(new LinksPanelEdit("linksPanel"));
        }

        form.add(isAllowedToViewStatistics()
                ? new StatisticsPanel("statPanel", pageOwner)
                : new EmptyPanel("statPanel"));

        form.add(infoPanel);
        form.add(avatarPanel);
        avatarPanel.setOutputMarkupId(true);
        AjaxFormValidatingBehavior.addToAllFormComponents(form, "onkeyup", Duration.ONE_SECOND);
    }

    private void initPerson(String login) {
        try {
            personDto = personService.getPersonByLogin(login);
        } catch (IllegalArgumentException e) {
            personDto = new PersonDto();
        }
    }

    private Panel generateLinksPanel() {
        Panel panel = new LinksPanel("linksPanel", personDto);
        if (((LinksPanel) panel).isEmptyPanel()) {
            panel = new EmptyPanel("linksPanel");
        }
        return panel;
    }

    private boolean isAllowedToViewStatistics() {
        org.apache.wicket.authroles.authorization.strategies.role.Roles rolesForViewingStatistics
                = new org.apache.wicket.authroles.authorization.strategies.role.Roles(ROLES_FOR_VIEWING_STATISTICS);
        SessionUser currentUser = ((HostelAuthenticatedWebSession) getSession()).getUser();
        return !Roles.COMMANDANT.equals(personDto.getRole())
                && !isEdited
                && (currentUser.hasAnyRole(rolesForViewingStatistics)
                    || (currentUser.hasRole(Roles.USER) && !isDifferentUserPage));
    }

    public void savePerson(AjaxRequestTarget target) {
        if (avatarPanel instanceof AvatarPanelEdit) {
            FileUpload uploader = ((AvatarPanelEdit) avatarPanel).getFileUpload().getFileUpload();
            if (uploader != null) {
                String fileName = uploader.getClientFileName();
                String fileExtension = getFileExtension(fileName);
                if (!isAllowedExtension(fileExtension)) {
                    notImageDialog.show(target);
                    return;
                }
                String avatarPath = uploadFile(uploader, fileExtension);
                if (infoPanel instanceof InfoPanelEditByAdmin) {
                    FacultyDto faculty = ((InfoPanelEditByAdmin) infoPanel).getFaculty();
                    personDto.setFaculty(faculty);
                }
                personDto.setAvatarPath(avatarPath);
            }
        }
        personService.savePerson(personDto, pageOwner);
        PageParameters parameters = new PageParameters();
        if (isDifferentUserPage) {
            parameters.add(USERNAME_KEY, pageOwner);
        } else {
            parameters.add(USERNAME_KEY, currentUserName);
        }
        setResponsePage(PersonPage.class, parameters);
        target.add(feedback);
    }

    private String uploadFile(FileUpload uploader, String fileExtension) {
        String avatarPath = personDto.getAvatarPath();
        if (uploader != null) {
            String realPath = UploadUtils.getAvatarFolderFullPath(getApplication());
            File avatarFolder = new File(realPath);
            boolean avatarFolderExists = true;
            if (!avatarFolder.exists()) {
                avatarFolderExists = avatarFolder.mkdirs();
            }
            if (avatarFolderExists) {
                if (deleteExistingFile(avatarFolder)) {
                    avatarPath = doUpload(uploader, fileExtension, realPath);
                }
            }
        }
        return avatarPath;
    }

    private String doUpload(FileUpload uploader, String fileExtension, String realPath) {
        String avatarPath = RandomStringUtils.randomAlphabetic(RANDOM_STRING_LENGTH) + "." + fileExtension;
        File newFile = new File(realPath + "/" + avatarPath);
        try {
            newFile.createNewFile();
            uploader.writeTo(newFile);
        } catch (Exception e) {
            avatarPath = null;
            LOG.error("Could not write file.", e);
        }
        return avatarPath;
    }

    private boolean deleteExistingFile(File avatarFolder) {
        for (File file : avatarFolder.listFiles()) {
            String fName = file.getName().substring(0, file.getName().lastIndexOf("."));
            if (file.isFile() && currentUserName.equals(fName)) {
                return file.delete();
            }
        }
        return true;
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private boolean isAllowedExtension(String extension) {
        return ALLOWED_EXTENSIONS.contains(extension.toLowerCase());
    }

    private ModalWindow createNotificationWindow(String id, String headerKey,
                                                 String contentKey, boolean showCancelButton) {
        return new NotificationWindow(id, headerKey, contentKey, showCancelButton) {
            @Override
            public void onCancel(AjaxRequestTarget target) {
                close(target);
            }

            @Override
            public void onConfirm(AjaxRequestTarget target) {
                close(target);
            }
        };
    }

    private class ProfileNavigationPanel extends NavigationPanel {

        public ProfileNavigationPanel(String id) {
            super(id, true);
        }

        @Override
        protected void onEmptySelected(AjaxRequestTarget target,
                                       RoomDto selectedRoom, FloorDto selectedFloor, HostelDto selectedHostel) {
            super.onEmptySelected(target, selectedRoom, selectedFloor, selectedHostel);
            ((InfoPanelEditByAdmin) infoPanel).setRoom(selectedRoom, selectedFloor, selectedHostel);
        }

        @Override
        protected void onPersonDeselected(AjaxRequestTarget target) {
            initializeSelectedFields();
            target.add(navigationPanel);
        }

        public void initializeSelectedFields() {
            super.initializeSelectedFields(personDto.getRoom(), personDto.getFloor(), personDto.getHostel());
        }
    }
}
