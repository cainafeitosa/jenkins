@merge jte {
    allow_scm_jenkinsfile = false
}

template_methods {
    checkout_scm
    build_artifact
    build_image
    unit_test
    static_code_analysis
    publish_image
}

@merge libraries {
    agent
    utility
    git
    @merge docker {
        pod_template = "docker"
        registry = "http://registry.apps.lab.local"
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
    master_branch  =  /^[Mm]aster$/
    develop_branch =  /^[Dd]evelop(ment|er|)$/
    hotfix_branch  =  /^[Hh]ot[Ff]ix-/
    release_branch =  /^[Rr]elease-(\d+.)*\d$/
}