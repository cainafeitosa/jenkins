fields { 
    required {}
    optional {
        agent {
            image = String
        }
        k8s_credentials_id              = String
        k8s_server_url                  = String
        helmfile_remote                 = Boolean
        helmfile_repository_credentials = String
        helmfile_repository_url         = String
        helmfile_repository_branch      = String
        helmfile_path                   = String
    }
}