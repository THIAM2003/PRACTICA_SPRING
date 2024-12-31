package com.dailycodework.dreamshops.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dailycodework.dreamshops.exceptions.AlreadyExistsException;
import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Category;
import com.dailycodework.dreamshops.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // "Constructor inyection" Si se marcan las dependencias con final Spring las inyectará automaticamente.
//@RequiredArgsConstructor genera un constructor que incluye todos los campos final (private final) y los campos marcados
// como @NonNull en una clase lo cual puede cumplir una función similar a la inyección de dependencias por constructor en Spring.
// Si fuera netamente inyeccion por constructor tendria que escribir explicitamente esto:

    //private final MyDependency myDependency;

    // Constructor explícito- Si usamos @Required... no tenemos que escribir este constructor:
    //public MyService(MyDependency myDependency) {
        //this.myDependency = myDependency;
    //}

// En Spring, cuando tienes un constructor que inyecta dependencias, no necesitas explícitamente la anotación @Autowired antes del constructor.
// Spring es capaz de detectar automáticamente el constructor y utilizarlo para inyectar las dependencias necesarias. Además si uso solo el 
// @Autowired sin el constructor se llama inyeccion por campo, que no es una buena practica ya que pierde inmutabilidad.

//La anotación @Override sirve para indicar que un método sobrescribe otro definido en una clase padre o interfaz,

public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Categoria no encontrada"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    //Aquí el optional solo envuelve el objeto category, luego con el metodo filter se hace el manejo
    @Override
    public Category addCategory(Category category) {
       return Optional.of(category).filter(c -> !categoryRepository.existsByName(c.getName()))
        .map(categoryRepository :: save)
        .orElseThrow(() -> new AlreadyExistsException(category.getName() + "ya existe."));
    }

    //Optinal.ofNullable devuelve todo el obejto si es no null
    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id)).map(oldCategory ->{
            oldCategory.setName(category.getName());
            return categoryRepository.save(oldCategory);
        }).orElseThrow(()-> new ResourceNotFoundException("Categoria no encontrada"));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id)
            .ifPresentOrElse(categoryRepository::delete, ()-> {
                throw new ResourceNotFoundException("Categoria no encontrada");
            });
    }

}
