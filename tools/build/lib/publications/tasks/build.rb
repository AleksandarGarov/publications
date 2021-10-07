namespace :build do

  desc "Builds the lamba code of the sip-gateway"
  task :lambda do
    include Execution
    java_project_path = File.join($configuration.base, "src/publications-system/lambdas/publications")
    build_gradle_path = File.join(java_project_path, "build.gradle")
    java_output_path = File.join($configuration.out, "build/distributions")
    jar_file = File.join(java_output_path, "publications.zip")
    java_src_pattern = "#{$configuration.base}/src/publications-system/lambdas/publications/src/**/*"
    gradlew = File.join(java_project_path, "gradlew")

    file jar_file => Rake::FileList[java_src_pattern, build_gradle_path] do
      cmdline = "#{gradlew} --console plain -p #{java_project_path} lambdaZip"
      run_cmdline("Build incremental distribution lambda", cmdline)
    end
    Rake::Task[jar_file].invoke
  end

end