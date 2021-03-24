#!/usr/bin/env groovy

fields {
  	optional {
		checkout_scm {
			excluded_message = String
			excluded_regions = String
			included_regions = String
		}
  	}
}