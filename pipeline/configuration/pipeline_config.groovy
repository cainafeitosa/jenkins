@merge jte {
    allow_scm_jenkinsfile = false
}

template_methods {
    checkout_scm
    build_artifact
    build_container_image
    unit_test
    static_code_analysis
    scan_container_image
    publish_container_image
    publish_helm_package
}

@merge libraries {
    agent {
        kubernetes
    }
    git
    utility
    @merge docker {
        pod_template   = "docker"
        credentials_id = "registry-credential"
    }
}

stages {
    continuous_integration {
        build_artifact
        parallel build_container_image, build_helm_package
        parallel unit_test, static_code_analysis, scan_container_image
        parallel publish_container_image, publish_helm_package
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