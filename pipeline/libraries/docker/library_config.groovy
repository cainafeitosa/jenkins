#!/usr/bin/env groovy

fields {
  	optional {
        pod_template = String
        registry = String
        image = String
        credentials_id = String
        dockerfile = String
        context_path = String
  	}
}