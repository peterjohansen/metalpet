# Template recipe

A template recipe, also called a Metalpet recipe, defines the input values and
execution steps needed to adopt a template as a new project.

By convention, the recipe file is named `metalpet.json` and lives in the
template root. Other supported formats may use the same basename with a
different extension, such as `metalpet.toml` or `metalpet.yaml`.

## Formats

| Format | Extensions      | Status                           |
| ------ | --------------- | :------------------------------- |
| JSON   | `.json`         | Default and officially preferred |
| TOML   | `.toml`         | Supported                        |
| YAML   | `.yaml`, `.yml` | Supported                        |

Other supported formats are available for templates whose ecosystem already
favors TOML or YAML.

## Schemas

### Recipe projects

- [`RecipeProject`](recipe-projects.md#recipeproject)
- [`RecipeInfo`](recipe-projects.md#recipeinfo)
- [`RecipeStep`](recipe-projects.md#recipestep)

### Values

- [`ValueSet`](recipe-values.md#valueset)
- [`ValueDefinition`](recipe-values.md#valuedefinition)

### Operations

- [`Operation`](recipe-operations.md#operation)
  - [`FileCopyOperation`](recipe-operations.md#filecopyoperation)

## Examples

Examples of recipes can be found in the [`examples/`](../examples) directory:

- [`java-maven-template`](../examples/java-maven-template/metalpet.json)
