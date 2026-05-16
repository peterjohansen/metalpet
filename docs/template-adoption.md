# Template adoption

Template adoption is the process of creating a new project from a template. This
is the core system process of Metalpet, driven according to the rules defined by
a [template-specific recipe](template-recipe.md).

The high-level adoption process for a typical template:

1. Read the template recipe in `metalpet.json`
2. Gather needed user input per the recipe
3. Copy and rename the template project directory
4. Perform the steps according to template recipe

## Engine capabilities

- Request input from the user
- Conditional step execution
- Replace content of specific properties in `.yaml` files
  - Replace content of specific tags in `.xml` files
- Search and replace by regex in files
- Search and replace text in files
- Target files by pattern
- Target specific files
