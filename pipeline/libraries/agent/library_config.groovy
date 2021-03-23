#!/usr/bin/env groovy

fields {
  	optional {
    	kubernetes {
        	cloud = String
        	pod_templates = String
    	}
    	label = String
	}
}