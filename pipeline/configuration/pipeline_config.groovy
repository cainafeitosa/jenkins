@merge jte {
	allow_scm_jenkinsfile = false
}

@merge libraries {
	kubernetes_agent
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