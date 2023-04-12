# !/bin/bash

# 현재 프로젝트 명. ex) Motti
project_name=Bin_System

# Local Maven Repository 경로
local_occidere_maven_repo='C:\Users\Febrie\IdeaProjects\Bin_System'

# Local Maven Repository의 snapshots 폴더로 deploy 실행
mvn -Dmaven.test.skip=true -DaltDeploymentRepository=snapshot-repo::default::file://${local_occidere_maven_repo}/snapshots clean deploy

# Local Maven Repository로 이동
# shellcheck disable=SC2164
cd ${local_occidere_maven_repo}

# git add & commit & push 진행
# deploy key를 등록했기 때문에 id, pw 입력 없이 진행 가능
git status
git add .
git status
git commit -m "release new version of ${project_name}"
git push origin master