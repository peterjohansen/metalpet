# Recipe schemas

- [`RecipeProject`](#recipeproject)
- [`RecipeInfo`](#recipeinfo)
- [`RecipeStep`](#recipestep)

## RecipeProject

| Field       | Value                                   |
| ----------- | --------------------------------------- |
| Kind        | Object                                  |
| Description | The top-level object defining a recipe. |

### Properties

| Property    | Type           | Required | Description                     |
| ----------- | -------------- | :------: | ------------------------------- |
| `meta`      | `RecipeInfo`   |   Yes    | Recipe metadata.                |
| `variables` | `ValueSet`     |   Yes    | Named input and derived values. |
| `steps`     | `RecipeStep[]` |   Yes    | Ordered adoption steps.         |

---

## RecipeInfo

| Field       | Value                                             |
| ----------- | ------------------------------------------------- |
| Kind        | Object                                            |
| Description | Information about the recipe itself as a project. |

### Properties

| Property      | Type     | Required | Description                             |
| ------------- | -------- | :------: | --------------------------------------- |
| `name`        | `String` |   Yes    | The display name of the recipe project. |
| `description` | `String` |    No    | Description of the recipe project.      |

---

## RecipeStep

| Field       | Value                                                                             |
| ----------- | --------------------------------------------------------------------------------- |
| Kind        | Object                                                                            |
| Description | A group of logically related operations that form part of a higher-level process. |

### Properties

| Property     | Type          | Required | Description                       |
| ------------ | ------------- | :------: | --------------------------------- |
| `name`       | `String`      |    No    | Name of the step.                 |
| `operations` | `Operation[]` |   Yes    | Operations that make up the step. |

## TODO

- `FileSelection` — Selects one or more files
  - By glob pattern
- `Condition` — Logical (boolean) expression
  - Using a variable (e.g., yes/no input from user)
- `PresetOperation` — A wrapper operation that applies preset operation
  configuration for commonly used
- `Value` — Any sort of value
  - String shorthand: `"hello world"`
  - Number shorthand: 42
  - Boolean shorthand: false
- `ValueReference : Value` — A reference to some value as opposed to an inline
  value
  - Namespaces to indicate value location
    - `meta` for meta recipe values
    - `vars` for recipe variables
    - etc.
  - Default values needed? Not for `vars` at least, but maybe other use cases.
  - String shorthand: `${<namespace>.<variable-name>}` (e.g., `${vars.name}`)
- `CasingTransformer` — Transforms text between various capitalization styles
  - Title Case
  - Sentence case
  - PascalCase
  - camelCase
  - snake_case
  - kebab-case
