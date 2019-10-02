#!/usr/bin/env sh

# Use Travis to trigger a release from Master

GITHUB_ORGANIZATION=jpmorganchase
GITHUB_REPOSITORY_NAME=java-lint-assert

# Assumptions
# - This is called from the root of the project
# - The travis client is installed: gem install travis
# - travis login --org has been called to authenticate

TRAVIS_PERSONAL_TOKEN=$(travis token)

#AN: line 17 breaks - seems that TRAVIS_PERSONAL_TOKEN has to be wrapped in quotes
#echo $TRAVIS_PERSONAL_TOKEN
: ${TRAVIS_PERSONAL_TOKEN:?"TRAVIS_PERSONAL_TOKEN needs to be set to access the Travis API to trigger the build"}

body='
{
	"request":
	{
		"branch": "master",
		"config":
		{
			"before_script": "export MANUAL_RELEASE_TRIGGERED=true"
		}
	}
}'

curl -s -X POST \
	-H "Content-Type: application/json" \
	-H "Accept: application/json" \
	-H "Travis-API-Version: 3" \
	-H "Authorization: token $TRAVIS_PERSONAL_TOKEN" \
	-d "$body" \
	https://api.travis-ci.org/repo/$GITHUB_ORGANIZATION%2F$GITHUB_REPOSITORY_NAME/requests
	
	
#curl -H "Travis-API-Version: 3" -H "Authorization: token aFaa9p4cR1BoHm6OwhK_cg" https://api.travis-ci.org/lint 