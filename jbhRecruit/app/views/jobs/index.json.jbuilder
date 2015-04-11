json.array!(@jobs) do |job|
  json.extract! job, :id, :jobId, :title, :department, :category, :description, :siteID, :postingDate, :fullTime, :shift, :qualifications
  json.url job_url(job, format: :json)
end
