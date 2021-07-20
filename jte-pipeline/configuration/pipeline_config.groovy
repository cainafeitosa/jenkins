@merge jte {
    allow_scm_jenkinsfile = false
}

template_methods {
    build_artifact
    build_container_image
    unit_test
    static_code_analysis
    publish_container_image
    deploy_to
}

stages {
    continuous_integration {
        build_artifact
        build_container_image
        unit_test
        static_code_analysis
        publish_container_image
    }
}

@merge libraries {
    agent {
        kubernetes {
            cloud        = "kubernetes"
            inherit_from = "default"
        }
    }
    scm {
        author_name  = "Jenkins"
        author_email = "jenkins@localhost"
    }
    utility {
        notify {
            microsoft_teams_webhook = ""
        }
    }
    @merge docker {
        registry       = "https://registry.example.com.br"
        credentials_id = "nexus"
    }
    @merge kubernetes
}

application_environments {
    dev {
        long_name          = "Development"
        k8s_server_url     = "https://kubernetes.example.com.br"
        k8s_credentials_id = "kubernetes-user-token"
    }
    test {
        long_name          = "Test"
        k8s_server_url     = "https://kubernetes.example.com.br"
        k8s_credentials_id = "kubernetes-user-token"
    }
    prod {
        long_name            = "Production"
        k8s_server_url       = "https://kubernetes.example.com.br"
        k8s_credentials_id   = "kubernetes-user-token"
        promote_version_from = "test"
    }
}

keywords {
    feature = /^(feature|task|story)\/.+$/
    develop = /^(develop)$/
    master  = /^(master)$/
    hotfix  = /^hotfix\/(0|[1-9]\d*)\.(0|[1-9]\d*)\.(0|[1-9]\d*)(?:-((?:0|[1-9]\d*|\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\.(?:0|[1-9]\d*|\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?(?:\+([0-9a-zA-Z-]+(?:\.[0-9a-zA-Z-]+)*))?$/
    release = /^release\/(0|[1-9]\d*)\.(0|[1-9]\d*)\.(0|[1-9]\d*)(?:-((?:0|[1-9]\d*|\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\.(?:0|[1-9]\d*|\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?(?:\+([0-9a-zA-Z-]+(?:\.[0-9a-zA-Z-]+)*))?$/
}
