@merge jte {
    allow_scm_jenkinsfile = false
}

template_methods {
    build_artifact
    unit_test
    static_code_analysis
    integration_test
    build_container_image  
    scan_container_image
    publish_container_image
    helm_package
    helm_lint
    helm_push
}

@merge libraries {
    agent
    gitlab
    utility
    kubernetes
    @merge docker {
        pod_template   = "docker"
        credentials_id = "registry-credential"
    }
}

application_environments {
    dev {
        long_name = "Development"
    }
    test {
        long_name = "Test"
    }
    prod {
        long_name = "Production"
    }
}

keywords {
    master  = /^[Mm]aster$/
    develop = /^[Dd]evelop(ment|er|)$/ 
    feature = /^feature-.*$/
    hotfix  = /^[Hh]ot[Ff]ix-/
    release = /^[Rr]elease-(\d+.)*\d$/
}