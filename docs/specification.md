# Specification

## Terminology

- **Template recipe** — The template-specific configuration describing necessary
  input and steps required to adopt the template as a brand-new project.
  Typically found in `metalpet.toml` at the template repository root.

## Template adoption process

The high-level process the system follows to adopt a template.

1. Read the template recipe in `metalpet.toml`
2. Gather required user input as described
3. Copy the template project to its new location
4. Rename the new project directory
5. Perform adoption steps according to template recipe

## Adoption engine capabilities

- Request input from the user
- Conditional step execution
- Replace content of specific properties in `.yaml` files
- Replace content of specific tags in `.xml` files
- Search and replace by regex in files
- Search and replace text in files
- Target files by pattern
- Target specific files

## Recipe elements

- `FileSelection`
    - By glob pattern
- `Condition`
    - Yes/no input from user
- `Step`
- `Operation`
- `JsonPath`

```json
{
  "projectName": "Keystone",
  "description": "A fictitious template project to illustrate Metalpet usage."
}
```
