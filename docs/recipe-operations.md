# Recipe operations

- [`Operation`](#operation)
  - [`FileCopyOperation`](#filecopyoperation)

## Operation

| Field         | Value                                       |
| ------------- | ------------------------------------------- |
| Kind          | Discriminated union                         |
| Description   | Atomic action that performs a unit of work. |
| Discriminator | `operation`                                 |

### Variants

| Discriminator value | Type                | Description                 |
| ------------------- | ------------------- | --------------------------- |
| `"fileCopy"`        | `FileCopyOperation` | Copies a file or directory. |

---

## FileCopyOperation

| Field         | Value                       |
| ------------- | --------------------------- |
| Kind          | `Operation` variant         |
| Discriminator | `operation: "fileCopy"`     |
| Description   | Copies a file or directory. |

### Properties

| Property     | Type         | Required | Description                |
| ------------ | ------------ | :------: | -------------------------- |
| `operation`  | `"fileCopy"` |   Yes    | Discriminator value.       |
| `sourceFile` | `String`     |   Yes    | File or directory to copy. |
| `targetFile` | `String`     |   Yes    | Target file or directory.  |
