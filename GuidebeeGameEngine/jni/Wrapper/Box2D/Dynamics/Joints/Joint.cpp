#include "Joint.h"
#include <Box2D/Box2D.h>

JNIEXPORT jint JNICALL
Java_com_guidebee_game_physics_Joint_jniGetType(JNIEnv* env, jobject object, jlong addr)
{
	b2Joint* joint = (b2Joint*)addr;
	return joint->GetType();
}

JNIEXPORT jlong JNICALL
Java_com_guidebee_game_physics_Joint_jniGetBodyA(JNIEnv* env, jobject object, jlong addr)
{
	b2Joint* joint = (b2Joint*)addr;
	return (jlong)joint->GetBodyA();
}

JNIEXPORT jlong JNICALL
Java_com_guidebee_game_physics_Joint_jniGetBodyB(JNIEnv* env, jobject object, jlong addr)
{
	b2Joint* joint = (b2Joint*)addr;
	return (jlong)joint->GetBodyB();
}

JNIEXPORT void JNICALL
Java_com_guidebee_game_physics_Joint_jniGetAnchorA(JNIEnv* env, jobject object,
    jlong addr, jfloatArray obj_anchorA)
{
	float* anchorA = (float*)env->GetPrimitiveArrayCritical(obj_anchorA, 0);
	b2Joint* joint = (b2Joint*)addr;
	b2Vec2 a = joint->GetAnchorA();
	anchorA[0] = a.x;
	anchorA[1] = a.y;
	env->ReleasePrimitiveArrayCritical(obj_anchorA, anchorA, 0);
}

JNIEXPORT void JNICALL
Java_com_guidebee_game_physics_Joint_jniGetAnchorB(JNIEnv* env, jobject object,
    jlong addr, jfloatArray obj_anchorB)
{
	float* anchorB = (float*)env->GetPrimitiveArrayCritical(obj_anchorB, 0);
	b2Joint* joint = (b2Joint*)addr;
	b2Vec2 a = joint->GetAnchorB();
	anchorB[0] = a.x;
	anchorB[1] = a.y;
	env->ReleasePrimitiveArrayCritical(obj_anchorB, anchorB, 0);
}

JNIEXPORT jboolean JNICALL
Java_com_guidebee_game_physics_Joint_jniGetCollideConnected(JNIEnv* env, jobject object, jlong addr)
{
	b2Joint* joint = (b2Joint*) addr;
	return joint->GetCollideConnected();
}

JNIEXPORT void JNICALL
Java_com_guidebee_game_physics_Joint_jniGetReactionForce(JNIEnv* env, jobject object,
    jlong addr, jfloat inv_dt, jfloatArray obj_reactionForce)
{
	float* reactionForce = (float*)env->GetPrimitiveArrayCritical(obj_reactionForce, 0);
	b2Joint* joint = (b2Joint*)addr;
	b2Vec2 f = joint->GetReactionForce(inv_dt);
	reactionForce[0] = f.x;
	reactionForce[1] = f.y;
	env->ReleasePrimitiveArrayCritical(obj_reactionForce, reactionForce, 0);
}

JNIEXPORT jfloat JNICALL
Java_com_guidebee_game_physics_Joint_jniGetReactionTorque(JNIEnv* env, jobject object,
    jlong addr, jfloat inv_dt)
{
	b2Joint* joint = (b2Joint*)addr;
	return joint->GetReactionTorque(inv_dt);
}

JNIEXPORT jboolean JNICALL
Java_com_guidebee_game_physics_Joint_jniIsActive(JNIEnv* env, jobject object, jlong addr)
{
	b2Joint* joint = (b2Joint*)addr;
	return joint->IsActive();
}

