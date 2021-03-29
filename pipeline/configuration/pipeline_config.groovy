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
    master  =  /^[Mm]aster$/
    develop =  /^[Dd]evelop(ment|er|)$/
    hotfix  =  /^[Hh]ot[Ff]ix-/
    release =  /^[Rr]elease-(\d+.)*\d$/
}

template_methods {
    package
    build_image
    unit_test
    static_code_analysis
    publish_image
}

stages {
    build {
        package
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