# Kubernetes

## Secrets
In new deployment we're securing the password of our SMTP account  by creating a secret.
```
kubectl create secret generic exams-secret --from-literal=smtp-passsword='yourpassword'
```
This way we don't have to expose our password in the deployment file.

## Nginx Ingress
Runt the following command to install nginx ingress. It will setup a new load balancer in the account.
```
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v0.44.0/deploy/static/provider/do/deploy.yaml
```

[Reference](https://kubernetes.github.io/ingress-nginx/deploy/#digital-ocean)

once the load balancer is ready and you can see the public IP on ingress apply the ingress file.
Check the ingress using following command.
```
kubectl get ingress
``` 

Apply the ingress file using
```
kubectl apply -f exams-ingress.yaml
```



