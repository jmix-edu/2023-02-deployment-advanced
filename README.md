### Project description 

This project is a sample of configuration for deploying application using `docker-compose` or `Kubernetes`.

### Minikube and Windows issues.

You can face a problem in Windows if you try to deploy your application from local Docker image.
Minikube docker daemon does not see images from local Docker. So you should build an image using
Minikube docker daemon.

To achieve this, follow the following steps:
1) Open `PowerShell`
2) Execute `minikube docker-env`
3) Then execute `minikube -p minikube docker-env --shell powershell | Invoke-Expression`
4) Navigate to project dir: `cd C:\Users\...\project_dir`
5) Build an image using gradlew: `.\gradlew docker`
6) Then in project dir execute command to apply your configuration: `kubectl apply -f .\k8s`

Helpful links:
https://stackoverflow.com/questions/48376928/on-windows-setup-how-can-i-get-docker-image-from-local-machine
https://serverfault.com/questions/964307/kubernetes-deployment-failed-to-pull-image-with-local-registry-minikube

