module Terraform
  def run_terraform(tf_command, tf_config)
    include Execution
    Dir.chdir(tf_config) do
      run_cmdline("Terraform initialisation", "terraform init")
      cmdline = "terraform #{tf_command} -no-color"

      run_cmdline("Running terraform #{tf_command} on #{tf_config}", cmdline)
    end
  end
end