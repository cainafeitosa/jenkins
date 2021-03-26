@merge jte {
	allow_scm_jenkinsfile = false
}

@merge libraries {
	git
    kubernetes_agent {
        cloud = "kubernetes"
    }
    @merge docker {
        runs_on = "docker"
        registry = "https://registry.apps.lab.local"
        credentials_id = "nexus-credential"
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

keywords{
	master  =  /^[Mm]aster$/
	develop =  /^[Dd]evelop(ment|er|)$/
	hotfix  =  /^[Hh]ot[Ff]ix-/
	release =  /^[Rr]elease-(\d+.)*\d$/
}

@merge stages {
    @merge continuous_integration {}
}