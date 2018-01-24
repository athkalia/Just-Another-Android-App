This project uses the codecov.io service for data coverage metrics.
Important notes:
* There's a main configuration file called codecov.yml
* To validate it execute `curl -k --data-binary @codecov.yml https://codecov.io/validate`
* There's a handy browser plugin for displaying code coverage metrics
when looking at the code. See [here](https://docs.codecov.io/docs/browser-extension)
for details and [here]( https://chrome.google.com/webstore/detail/codecov-extension/keefkhehidemnokodkdkejapdgfjmijf) for the google
chrome version.
* Main configuration file with a lot of information is codecov.yml
* To upload the code coverage reports one simply needs to run this
command in CI (after the code coverage reports have been generated)
`bash <(curl -s https://codecov.io/bash) -t 5204de31-b32e-4ad0-b76e-4c4a862c4292 -Z -F <flag>`.
 Note that:
 * `-Z` causes the command to exit with `1` in case of an error,
 thus failing the build. Without this flag it exits with `0`, not failing
 the build.
 * `-F` uploads the coverage reports under this tag. This allows creating
 different reports for different groups of tests (like integration vs unit
 tests).
* In this project we have 3 flags: `unit_tests`, `espresso_tests`, `all`
* Strings contained in the .yaml file can be encrypted. Check
[this](https://codecov.io/gh/athkalia/Just-Another-Android-App/settings/yaml)
for details.
