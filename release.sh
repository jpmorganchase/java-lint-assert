#!/usr/bin/env bash
#if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    openssl aes-256-cbc -K $encrypted_81c98acad902_key -iv $encrypted_81c98acad902_iv -in codesigning.asc.enc -out codesigning.asc -d
    gpg --fast-import codesigning.asc

    mvn deploy -Prelease-sign-artifacts --settings settings.xml
#fi
