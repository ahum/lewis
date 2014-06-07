# Lewis

A project pipeline tool


You have a software project. From having the source code running/building on a developers machine to having it available for public use, there are many steps along the way.

### Examples

#### my-web-app

* developer-workstation-server
 * compile
 * unit-test
 * integration-test
 * run-server


* developer-server
  * compile
  * unit-test
  * integration-test
  * build-slug
  * deploy-slug
  * regression-test


* qa-server
   * backup-db
   * synch-s3
   * deploy-slug
   * regression-test


* staging-server
  * backup-db
  * copy-live-db
  * migrate-db
  * synch-assets
  * deploy-slug
  * regression-test


* production-server
 * backup-db
 * migrate-db
 * deploy-slug
 * regression-test
