# Template recipe

A template recipe (or Metalpet recipe) is template-specific configuration
describing tbe input and steps needed to adopt a template as a brand-new
project.

The recipe file is usually found as `metalpet.json` in the root directory. A
minimal example:

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
          "operation": "file-copy",
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

The officially preferred and default format is JSON. Though, you are free to
choose the format based on personal preference or other factors. Consider
whether your template project and its platform/language ecosystem has a
format preference for similar files.

## Schemas

### TemplateRecipe

The top-level object defining a template recipe.

#### Properties

- `meta: TemplateInfo`
- `variables: VariableSet`
- `steps: ExecutionStep[]`

### TemplateInfo

Information about the template itself as a project.

#### Properties

- `name: String` — Name of the template
- `description: String?` — Description of the template

### VariableSet

A set of variable assignments.

#### Properties

- `[key: String]: Value` — Zero or more key/value associations

### ExecutionStep

A group of logically-related operations part of a higher level process.

#### Properties

- `operations: Operation[]` — A list of operations that make up the step

### Operation

An atomic action that performs a unit of work.

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
