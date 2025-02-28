package id.ac.ui.cs.advprog.eshop.contoller;

import org.springframework.ui.Model;

public interface CrudController<T, ID> {
    String getCreatePage(Model model);
    String postCreate(T entity, Model model);
    String getListPage(Model model);
    String getEditPage(ID id, Model model);
    String postUpdate(ID id, T entity, Model model);
    String postDelete(ID id);
}