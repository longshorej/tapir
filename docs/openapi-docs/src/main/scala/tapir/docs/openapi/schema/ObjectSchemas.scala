package tapir.docs.openapi.schema

import tapir.Schema.{SCoproduct, SObject, SObjectInfo, SRef}
import tapir.openapi.OpenAPI.ReferenceOr
import tapir.openapi.{Discriminator, Schema => OSchema, _}
import tapir.{Schema => TSchema}

import scala.collection.immutable.ListMap

class ObjectSchemas(tschemaToOSchema: TSchemaToOSchema, schemaKeys: Map[SObjectInfo, (SchemaKey, ReferenceOr[OSchema])]) {
  def apply(schema: TSchema): ReferenceOr[OSchema] = {
    schema match {
      // by construction, references to all object schemas should be present in tschemaToOSchema
      case SObject(info, _, _) => tschemaToOSchema(SRef(info.fullName))
      case SCoproduct(schemas, d) =>
        Right(
          OSchema.apply(schemas.map(apply),
                        d.map(x => Discriminator(x.propertyName, Some(x.mapping.mapValues(v => apply(v.schema).left.get.$ref).toListMap)))))
      case _ => tschemaToOSchema(schema)
    }
  }

  def keyToOSchema: ListMap[SchemaKey, ReferenceOr[OSchema]] = schemaKeys.values.toListMap
}
