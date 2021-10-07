namespace :deploy do
  desc "Deploy to aws"
  task :publ => [:"build:lambda"] do
    include Terraform
    ENV["AWS_PROFILE"] = "publ"
    ENV["AWS_REGION"] = "eu-west-2"
    run_terraform("apply -auto-approve -lock=true", "src/publications-system/accounts/dev")
  end
end