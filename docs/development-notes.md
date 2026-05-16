# Notes

Various notes for the development of Metalpet.

## Ideas and thoughts

- JSON Schema likely useful in some way

### System interfaces

Metalpet could have several applications (inbound adapters) to perform the
template adoption process:

- Plain CLI (would require all input to be provided beforehand)
- Interactive CLI
- GUI

### User-defined operations

Allow user-defined operation? Using shell scripts? This has security
implications, as well as opening up for the introduction of unwanted
OS/platform-sepcific dependencies. Might not be needed if users are given other
ways to compose or otherwise reuse steps and operations.

## Adoption process playthrough

Template recipes could be "played through" using different approaches, depending
on user preference. Typically, it would be autonomous; each step in the adoption
process is applied automatically and instantly. However, the process could be
performed step-by-step, providing the user with greater insight and control.
This would be useful for several reasons, like debugging for developing recipes,
for security reason by carefully monitoring adoption of template projects the
user doesn't trust, etc.

## Concerns and challenges

### Security

How to handle security? The nature of recipes and their application open up the
possibility of malicious code execution of unsuspecting users. Strict policies
governing execution or potentially a sandbox are potential preventative
measures.
