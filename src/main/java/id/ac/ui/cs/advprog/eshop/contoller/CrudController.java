package id.ac.ui.cs.advprog.eshop.contoller;

import org.springframework.ui.Model;

public interface CrudController<T, I> {
    String getCreatePage(Model model);
    String postCreate(T entity, Model model);
    String getListPage(Model model);
    String getEditPage(I id, Model model);
    String postUpdate(I id, T entity, Model model);
    String postDelete(I id);
}