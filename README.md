# GraphAware Graphgen Procedure

## Generate Graph Test Data easily in your Neo4j browser

[![Build Status](https://travis-ci.org/graphaware/neo4j-graphgen-procedure.svg?branch=master)](https://travis-ci.org/graphaware/neo4j-graphgen-procedure) Current release: (no release) - 1.0-ALPHA

### Usage

* Clone this repository, run `mvn clean package` and put the produced `graphaware-graphgen-procedure-1.0-ALPHA.jar` file in the Neo4j's `plugins` folder
* Restart your Neo4j instance

### Generating nodes

Nodes are generated using the `generate.nodes()` procedure call. The procedure takes 3 arguments :

* A `string or list of strings` representing one or multiple labels (eg: `'Person'` or `['Person', 'User']`)
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
* An `integer or String` representing the amount of nodes each start node will connect to an end node

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

### Generating values

As the same way you can create node and relationships, you can generate values :

```cypher
CALL generate.values('firstName', null, 10) YIELD values as values RETURN *
```

the second argument takes parameters when value generators need them, like `numberBetween` for example :

```cypher
CALL generate.values('numberBetween', [10, 100], 25) YIELD values RETURN values
```

This will generate 25 randomly generated numbers between 10 and 100.

## Extra procedures

### Generating Linked Lists

```cypher
CALL generate.nodes('Feed', '{time: unixTime}', 10) YIELD nodes as feeds
CALL generate.linkedList(feeds, 'NEXT') YIELD relationships, nodes
RETURN *
```

Returns a linked list of feed nodes : `(feed)-[:NEXT]->(feed)-[:NEXT]->(feed)-[:NEXT]->(...)`


### Available data providers

#### Names

* `firstName`
* `lastName`
* `fullName`

#### Address

* `city`
* `country`
* `state`
* `streetAddress`
* `streetName`
* `zipCode`
* `latitude(min = -90, max = 90)`
* `longitude(min = -180, max = 180)`

#### Numbers

* `randomNumber`
* `randomFloat`
* `numberBetween(a, b)`

#### Business

* `creditCardNumber`
* `creditCardType`

#### Company

* `companyName`

#### Internet

* `avatarUrl`
* `email`
* `url`
* `ipv4`

#### Lorem

* `paragraph`
* `sentence`
* `word`

#### Phone

* `phoneNumber`

#### Time

* `unixtime`

License
-------

Copyright (c) 2013-2016 GraphAware

GraphAware is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.
If not, see <http://www.gnu.org/licenses/>.