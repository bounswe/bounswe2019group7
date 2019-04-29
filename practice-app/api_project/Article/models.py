from django.db import models
from django.contrib.auth.models import User

class Article(models.Model):
	article_title = models.CharField(max_length=100)
	article_text = models.TextField()
	owner = models.CharField(max_length=20)

	def __str__(self):
		return self.article_title