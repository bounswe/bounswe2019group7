from django.db import models


# TODO: API'dan veri cekilecek.
class Currency(models.Model):
    code = models.CharField(max_length=255)
    value = models.FloatField()
    id = models.IntegerField(primary_key=True)

    def __str__(self):
        return str(self.code)+ " - "+str(self.value)

