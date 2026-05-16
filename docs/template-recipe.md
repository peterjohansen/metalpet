# Template recipe

A template recipe, also called a Metalpet recipe, defines the input values and
execution steps needed to adopt a template as a new project.

By convention, the recipe file is named `metalpet.json` and lives in the
template root. Other supported formats may use the same basename with a
different extension, such as `metalpet.toml` or `metalpet.yaml`.

This minimal example asks for a project name and location, derives a directory
name, then copies the template into that directory:

```json
{
  "meta": {
    "name": "My template",
    "description": "A fictitious template illustrating a Metalpet recipe."
  },
  "variables": {
    "projectName": {
      "type": "string",
      "default": "Untitled",
      "description": "The official name of your project."
    },
    "projectLocation": {
      "type": "string",
      "format": "directory",
      "default": "../",
      "description": "The location of your project."
    },
    "directoryName": {
      "derived": "${vars.projectName}",
      "description": "The name of your project's main directory.",
      "transform": "kebabCase"
    }
  },
  "steps": [
    {
      "name": "Establish project directory",
      "operations": [
        {
          "operation": "fileCopy",
          "sourceFile": ".",
          "targetFile": "${vars.projectLocation}/${vars.directoryName}"
        }
      ]
    }
  ]
}
```

## Formats

Metalpet supports template recipes in these formats:

- JSON — `.json` (default)
- TOML — `.toml`
- YAML — `.yaml`, `.yml`

JSON is the officially preferred and default format. Other supported formats are
available for templates whose ecosystem already favors TOML or YAML.

## References

String values may reference recipe variables using `${vars.<name>}`. Recipe
paths are interpreted relative to the template root unless otherwise specified.

## Schemas

A trailing `?` marks an optional property. Array types use `Type[]`.

### TemplateRecipe

The top-level object defining a template recipe.

#### Properties

- `meta: TemplateInfo`
- `variables: VariableSet`
- `steps: ExecutionStep[]`

### TemplateInfo

Information about the template itself as a project.

#### Properties

- `name: String` — The display name of the template
- `description: String?` — Description of the template

### VariableSet

A set of named input and derived values.

#### Properties

- `[key: String]: VariableDefinition` — Zero or more named variable definitions

### VariableDefinition

A value needed by the adoption process.

Variables describe the required input, defaults, and derived values.

#### Properties

- `type: String?` — Type of input value
- `format: String?` — Expected value format
- `default: Value?` — Default value
- `derived: ValueReference?` — Source value used to derive this variable
- `transform: String?` — Transformation applied to the variable value
- `description: String?` — Description of the variable

### ExecutionStep

A group of logically related operations that form part of a higher-level
process.

#### Properties

- `name: String?` — Name of the step
- `operations: Operation[]` — A list of operations that make up the step

### Operation

A discriminated union of all supported operation types. Each operation is an
atomic action that performs a unit of work.

#### Properties

- `operation: String` — Discriminator selecting the concrete operation type

#### Variants

- `"fileCopy"` — `FileCopyOperation`

### FileCopyOperation

Copies a file or directory.

#### Type

`Operation`

#### Properties

- `operation: "fileCopy"`
- `sourceFile: String` — File or directory to copy
- `targetFile: String` — Target file or directory

### TODO

- `FileSelection` — Selects one or more files
  - By glob pattern
- `Condition` — Logical (boolean) expression
  - Using a variable (e.g., yes/no input from user)
- `PresetOperation` — A wrapper operation that applies preset operation
  configuration
  for commonly used
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
