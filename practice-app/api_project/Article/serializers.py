from rest_framework import serializers
from Article.models import Article

class ArticleSerializer(serializers.ModelSerializer):
	class Meta:
		model=Article
		fields=("owner", "article_title", "article_text")