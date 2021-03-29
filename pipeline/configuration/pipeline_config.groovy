@merge libraries {
    git
	utility
    kubernetes_agent {
        cloud = "kubernetes"
    }
    @merge docker {
        runs_on {
            pod_template = "docker"
        }
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