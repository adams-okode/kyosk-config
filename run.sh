deploy_kubernertes_cluster_and_run() {
  kubectl apply -f deployment.yaml
  minikube service --url kyosk-service
}

deploy_kubernertes_cluster_and_run
