@merge jte {
    allow_scm_jenkinsfile = false
}

template_methods {
    checkout_scm
    build_artifact
    build_image
    unit_test
    static_code_analysis
    scan_image
    publish_image
}

@merge libraries {
    agent {
        kubernetes
    }
    utility
    git
    @merge docker {
        pod_template   = "docker"
        credentials_id = "registry-credential"
    }
}

stages {
    build {
        build_artifact
        build_image
    }
    test {
        unit_test
        static_code_analysis
        scan_image
    }
    release {
        publish_image
    }
}

application_environments {
    dev {
        long_name = "Development"
    }
    tst {
        long_name = "Test"
    }
    prd {
        long_name = "Production"
    }
}

keywords {
    master_branch  = /^[Mm]aster$/
    develop_branch = /^[Dd]evelop(ment|er|)$/ 
    feature_branch = /^feature-.*$/
    hotfix_branch  = /^[Hh]ot[Ff]ix-/ 
    release_branch = /^[Rr]elease-(\d+.)*\d$/
}