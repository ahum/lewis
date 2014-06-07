# lewis-core

A Clojure library designed to ... well, that part is up to you.

## Usage

### Install a pipeline

    lewis install git@github.com:corespring/container-pipeline.git
    > installing pipeline ...
    > installing repo ...
    > triggering 1st build ... commit hash: 12345678
    > done:
    >  installed container-pipeline to ~/.lewis/projects/container-pipeline
    >  flow dev:12345678 completed successfully

### List pipelines

    lewis list
    > container-pipeline (3 flows) (1 run (1/1 successful))

### pipeline info

    lewis info container-pipeline

    !scm-change ---> dev ----------> qa -----------> staging -----> live
                     | test          | backup-db     | ...
                     | it            | copy-db
                     | slug          | migrate-db
                     | deploy        | deploy

                     latest: 10      latest: 10      latest: 8      latest: 3
    > flows:
      dev:
      qa: 4, 5, 6
      staging: ...
      live: ...

### run

    lewis container-pipeline flow:qa
    > commit hash: 123
    > dev:123 exists and was successful? yes
    > run qa:123:build-slug
    > run qa:123:deploy-slug

    > dev:123 doesn't exist run `flow:dev` again
