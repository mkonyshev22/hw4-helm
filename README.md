 ## Запуск приложения

```bash
# Запускаем minikube
minikube start
```
```bash
# Запускаем СУБД Postgres
helm install my-release oci://registry-1.docker.io/bitnamicharts/postgresql \
  --set auth.username=user5 \
  --set auth.password=password5 \
  --set auth.database=postgres
```
```bash
# Подготовка учетных данных для подключения к БД. Заполняем kube/secret.yaml и kube/configmap.yaml
echo -n 'user5' | base64
echo -n 'password5' | base64
```
```bash
# Применяем конфиг и серкеты
kubectl apply -f ./kube/config/ 
```
```bash
# Применяем миграции
kubectl apply -f ./kube/migration/
```
```bash
# Дожидаемся завершения работы миграций
kubectl get jobs
```
```bash
# Запускаем основное приложение
kubectl apply -f ./kube/app/
```
```bash
# Запускаем ingress
minikube addons enable ingress
```
```bash
# Проверяем что ingress запущен
minikube addons list
```
```bash
# Запускаем minikube tunnel
minikube tunnel
```

## Дополнительные команды
```bash
# Сборка приложения для миграции
mvn clean package
docker build --platform linux/amd64 -t otus-hw4-liqui .
docker tag otus-hw4-liqui mksikayo/otus-hw4-liqui
docker push mksikayo/otus-hw4-liqui
```
```bash
# Сборка основного приложения
mvn clean package
docker build --platform linux/amd64 -t otus-hw4-app .
docker tag otus-hw4-app mksikayo/otus-hw4-app
docker push mksikayo/otus-hw4-app
```
```bash
kubectl get all
```
```bash
# Удаляем СУБД Postgres
helm uninstall my-release
```
```bash
# Получаем PVC
kubectl get pvc
```
```bash
# Удаляем PVC (без этого не получится обносить учетные данный для postgres)
kubectl delete pvc _data_
```