from django.db import models
from django.contrib.auth.models import User


class Portfolio(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE)
    id = models.IntegerField(primary_key=True)
    username = models.CharField(max_length=255,default="")

    def __str__(self):
        return str(User.username) + "'s Portfolio"
