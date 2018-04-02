package guru.springframework.bootstrap;


import guru.springframework.domain.*;
import guru.springframework.repositories.*;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent>{

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeRepository recipeRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
    }

    private Set getRecipes() {

        Optional<Category> mexicanCategory = categoryRepository.findByDescription("Mexican");
        Category mexican = null;
        if (mexicanCategory.isPresent())
            mexican = mexicanCategory.get();
        Optional<Category> americanCategory = categoryRepository.findByDescription("American");
        Category american = null;
        if (americanCategory.isPresent())
            american = americanCategory.get();

        Optional<UnitOfMeasure> tablespoon = unitOfMeasureRepository.findByDescription("Tablespoon");
        UnitOfMeasure tabls = null;
        if (tablespoon.isPresent())
            tabls = tablespoon.get();
        Optional<UnitOfMeasure> teaspoon = unitOfMeasureRepository.findByDescription("Teaspoon");
        UnitOfMeasure teas = null;
        if (teaspoon.isPresent())
            teas = teaspoon.get();

        Set recipes = new HashSet();
        Recipe spicyChicken = new Recipe();
        spicyChicken.setCookTime(15);
        spicyChicken.getCategories().add(mexican);
        spicyChicken.getCategories().add(american);
        spicyChicken.setDescription("Spicy Grilled Chicken Tacos");
        spicyChicken.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.");
        spicyChicken.setNotes(new Notes(spicyChicken, "Look for ancho chile powder with the Mexican ingredients at your grocery store, on buy it online. (If you can't find ancho chili powder, you replace the ancho chili, the oregano, and the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)"));
        spicyChicken.setPrepTime(20);
        spicyChicken.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        spicyChicken.setDifficulty(Difficulty.MODERATE);
        spicyChicken.addIngredient(new Ingredient("ancho chili powder", BigDecimal.valueOf(2), tabls));
        spicyChicken.addIngredient(new Ingredient("dried oregano", BigDecimal.valueOf(2), teas));
        recipes.add(spicyChicken);

        Recipe perfectGuacamole = new Recipe();
        perfectGuacamole.setCookTime(0);
        perfectGuacamole.getCategories().add(mexican);
        perfectGuacamole.getCategories().add(american);
        perfectGuacamole.setDescription("Perfect Guacamole");
        perfectGuacamole.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.");
        perfectGuacamole.setNotes(new Notes(perfectGuacamole, "Be careful handling chiles if using. Wash your hands thoroughly after handling and do not touch your eyes or the area near your eyes with your hands for several hours."));
        perfectGuacamole.setPrepTime(15);
        perfectGuacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        perfectGuacamole.setDifficulty(Difficulty.EASY);
        perfectGuacamole.addIngredient(new Ingredient("Kosher salt", BigDecimal.valueOf(0.5), teas));
        perfectGuacamole.addIngredient(new Ingredient("avocados", BigDecimal.valueOf(2), null));
        recipes.add(perfectGuacamole);

        return recipes;

    }

}
