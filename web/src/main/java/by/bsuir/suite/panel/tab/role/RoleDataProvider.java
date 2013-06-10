package by.bsuir.suite.panel.tab.role;

import by.bsuir.suite.dto.role.RoleDto;
import by.bsuir.suite.service.RoleService;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: DenisKravchenko
 * Date: 14.07.12
 * Time: 18:06
 * To change this template use File | Settings | File Templates.
 */
public class RoleDataProvider extends SortableDataProvider<RoleDto> {

    @SpringBean
    private RoleService roleService;

    public RoleDataProvider() {
        Injector.get().inject(this);
    }

    @Override
    public Iterator<? extends RoleDto> iterator(int first, int count) {
        return roleService.findFrom(first, count).iterator();
    }

    @Override
    public int size() {
        return roleService.count().intValue();
    }

    @Override
    public IModel<RoleDto> model(RoleDto object) {
        return new DetachableRoleModel(object);
    }
}
