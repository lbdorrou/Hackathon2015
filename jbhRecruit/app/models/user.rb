class User < ActiveRecord::Base
  # Include default devise modules. Others available are:
  # :confirmable, :lockable, :timeoutable and :omniauthable
  devise :database_authenticatable, :registerable,
         :recoverable, :rememberable, :trackable, :validatable
  has_attached_file :resume
  validates_attachment_content_type :resume, content_type: ['image/jpeg', 'image/png', 'image/gif', 'application/pdf']
end
