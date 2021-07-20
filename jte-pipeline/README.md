# Jenkins Templating Engine

## Uso
Para configuração dos pipelines de CI/CD utilizados no [jenkins](https://jenkins.example.com.br), primeiramente deve ser criado no repositório do projeto um arquivo chamado pipeline_config.groovy importando as bibliotecas que o projeto ira utilizar.

Exemplo de utilização para um projeto Maven.

```groovy
libraries {
    maven
}
```

Por padrão a configuração definida no Jenkins já importa as bibliotecas (agent, scm, utility, docker, kubernetes) tornando mais minimalista as configurações para o projeto.

## Bibliotecas
- agent
- docker
- helm
- kubernetes
- maven
- npm
- scm
- utility

### Maven
#### Parâmetros:
```groovy
libraries {
    maven {
        agent {
            image (Optional) Default: "maven:3-jdk-11-alpine"
        }
        work_dir (Optional) Default: "."
        skip_test (Optional) Default: false
    }
}
```
### NPM
#### Parâmetros:
```groovy
libraries {
    npm {
        agent {
            image (Optional) Default: "node:10-alpine"
        }
        work_dir (Optional) Default: "."
        registry (Optional)
        skip_test (Optional) Default: false
    }
}
```
### Docker
#### Parâmetros:
```groovy
libraries {
    docker {
        agent {
            image (Optional) Default: "docker:19.03"
        }
        image_name (Optional) Default: "GitLab Project Name"
        dockerfile_path (Optional) Default: "Dockerfile"
        context_path (Optional) Default: "."
        modules (Optional) Default: {}
        build_args {} (Optional) Default: {}
    }
}
```
Para múltiplas imagens no mesmo projeto deve-se utilizar o parâmetro "modules", sendo eles o primeiro nível de diretório do projeto. Exemplo:

```bash
[user@host project]$ ll *
api:
total 4
-rw-rw-r-- 1 user user    0 jul 19 18:52 Dockerfile
drwxrwxr-x 2 user user 4096 jul 19 18:52 src

migrations:
total 0
-rw-rw-r-- 1 user user 0 jul 19 18:52 db
-rw-rw-r-- 1 user user 0 jul 19 18:52 Dockerfile
```

```groovy
libraries {
    docker {
        modules {
            backend
            migrations 
        }
    }
}
```
