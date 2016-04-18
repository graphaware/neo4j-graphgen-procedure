# GraphAware Graphgen Procedure

## Generate Graph Test Data easily in your Neo4j browser

### Usage

* Download and place the `graphaware-graphgen-procedure` in your Neo4j's `plugins` directory
* Restart your Neo4j instance

### Generating nodes

Nodes are generated using the `generate.nodes()` procedure call. The procedure takes 3 arguments :

* A `string` representing one or multiple labels (eg: `'Person'` or `'Person,User'`)
* An `inline yaml string` definition of key/value pairs where value represent a data generator (eg: `'{firstname: firstName, age:{numberBetween:[18,35]}}'`)
* An `integer` representing the amount of nodes to be generated

Example :

```cypher
CALL generate.nodes('Person', '{name: fullName}', 10)
```

The procedure returns a `List` of Node objects for further processing, they can explicitely be yielded with the `nodes` name :

```cypher
CALL generate.nodes('Person', '{name: fullName}', 10) YIELD nodes as persons RETURN persons
```


### Generating relationships

Relationships are generated using the `generate.relationships` procedure call. The procedure takes 6 arguments :

* A `List` of nodes to start from
* A `List` of nodes to end to
* A `String` representing the relationship type
* An `inline yaml` definition of key/value pairs where value represent a data generator (eg: `'{since: unixTime}'`)
* An `integer or String` representing the amount of nodes from the start list to use
* An 'integer or String' representing the amount of nodes each start node will connect to an end node

The two last parameters are dynamic, for example :

* `1` exactly one node to be used from the given list
* `1-5` a random number between 1 and 5 will be generated and used as amount

Example :

```cypher
CALL generate.nodes('Person', '{name: fullName}', 10) YIELD nodes as persons
CALL generate.nodes('Skill', '{name: word}', 20) YIELD nodes as skills
CALL generate.relationships(persons, skills, 'HAS_SKILL', '{since: unixTime}', 10, '3-7')
YIELD relationships as relationships RETURN *
```

### Available data providers

#### Names

* `firstName`
* `lastName`
* `fullName`

#### Address

* `city`
* `country`

#### Numbers

* `randomNumber`
* `randomFloat`
* `numberBetween(a, b)`
