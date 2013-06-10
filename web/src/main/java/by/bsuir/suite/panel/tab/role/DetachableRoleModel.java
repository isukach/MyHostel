package by.bsuir.suite.panel.tab.role;

import by.bsuir.suite.dto.role.RoleDto;
import by.bsuir.suite.service.RoleService;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;


/**
 * Created by IntelliJ IDEA.
 * User: DenisKravchenko
 * Date: 14.07.12
 * Time: 17:53
 * To change this template use File | Settings | File Templates.
 */
public class DetachableRoleModel extends LoadableDetachableModel<RoleDto> {
    private final Long id;

    @SpringBean
    private RoleService roleService;

    public DetachableRoleModel(Long id){
        Injector.get().inject(this);
        if(id == null){
            throw new IllegalArgumentException();
        }
        this.id = id;
    }

    public DetachableRoleModel(RoleDto role){
        this(role.getId());
    }

    @Override
    protected RoleDto load() {
        return roleService.get(id);
    }
}
