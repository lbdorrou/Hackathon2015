class CreateApplies < ActiveRecord::Migration
  def change
    create_table :applies do |t|
      t.text :user
      t.text :jobId
      t.date :date

      t.timestamps null: false
    end
  end
end
