# Recipe values

- [`ValueSet`](#valueset)
- [`ValueDefinition`](#valuedefinition)

## ValueSet

| Field       | Value                  |
| ----------- | ---------------------- |
| Kind        | Map                    |
| Key type    | `String`               |
| Value type  | `ValueDefinition`      |
| Cardinality | Zero or more           |
| Description | A set of named values. |

---

## ValueDefinition

| Field       | Value                                                       |
| ----------- | ----------------------------------------------------------- |
| Kind        | Object                                                      |
| Description | A named value.                                              |
| Scope       | Describes any input, default, derived, and constant values. |

### Properties

| Property      | Type             | Required | Description                                   |
| ------------- | ---------------- | :------: | --------------------------------------------- |
| `type`        | `String`         |    No    | Type of input value.                          |
| `format`      | `String`         |    No    | Expected value format.                        |
| `default`     | `Value`          |    No    | Default value.                                |
| `derived`     | `ValueReference` |    No    | Source value used to derive this variable.    |
| `transform`   | `String`         |    No    | Transformation applied to the variable value. |
| `description` | `String`         |    No    | Description of the variable.                  |
