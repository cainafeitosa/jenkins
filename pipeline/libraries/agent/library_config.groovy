#!/usr/bin/env groovy

fields {
  optional {
    kubernetes {
        cloud = String
        podTemplates = String
    }
    label = String
  }
}