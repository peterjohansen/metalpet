# Philosophy

A collection of various thoughts and ideas that help concretize the original
vision of Metalpet.

## Template drift

Projects created from adopting a template will naturally drift from their
original template over time. This is not only acceptable but expected as
projects grow and mature.

## Template synchronicity

While keeping projects up to date with templates is not an expectation, it is
not discouraged or frowned upon either. Tools and utilities to simplify the
process of being in sync with the original template are provided for those who
wish to do so.

## Data/presentation separation

Template recipes express the required input and steps needed to adopt a project,
but do not concern themselves with how the adoption process interacts with the
user or how it is presented.

For example, a recipe may describe that a certain input value is needed, but it
does not dictate how or when the user is asked to provide the value.

## Object model readability

The template recipe object model should strive to remain readable and
understandable based on property names alone - without the context of type
names.

## Object model correctness vs. terseness

The template recipe object model should be designed to be robust, flexible, and
strictly "correct" from a data structure perspective while at the same time
being expressible in a non-terse manner with minimal boilerplate for humans.

This is achieved through carefully selected support for shorthand syntax of
commonly used expressions and declarations, which are at runtime expanded to
their full object model equivalents. For example, a string value might be
expressed in shorthand as:

```json
"hello world"
```

Which in its full representation is actually:

```json
{
  "type": "string",
  "value": "hello world"
}
```

## Platform independency

Metalpet should strive to remain as platform/OS independent as possible.
Deviations from this rule should only occur in highly special cases where the
value gained far outweighs the costs, or for truly platform-specific operations
where anything else would be nonsensical. 
