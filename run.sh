#!/bin/bash
deploy_kubernetes_cluster_and_run() {
#  minikube addons enable ingress
  kubectl apply -f arch.yaml
  minikube service kyosk-deployment-service --url
}
deploy_kubernetes_cluster_and_run